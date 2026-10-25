pipeline {
    agent any

    parameters {
        choice(name: 'PLATFORM', choices: ['android', 'ios'], description: 'Target mobile platform')
        choice(name: 'SUITE', choices: ['testng.xml', 'testng-smoke.xml'], description: 'TestNG suite to run')
        string(name: 'APPIUM_URL', defaultValue: 'http://127.0.0.1:4723', description: 'Appium server URL')
    }

    options {
        timeout(time: 60, unit: 'MINUTES')
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B clean test-compile'
            }
        }

        stage('Test') {
            steps {
                // Requires an Appium server and a connected device/emulator reachable from the agent.
                sh """
                    mvn -B test \
                        -Dplatform=${params.PLATFORM} \
                        -Dsuite=${params.SUITE} \
                        -Dappium.server.url=${params.APPIUM_URL}
                """
            }
        }
    }

    post {
        always {
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
            archiveArtifacts artifacts: 'target/allure-results/**', allowEmptyArchive: true
        }
    }
}
