pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr:'6'))
        timeout(time: 10, unit: 'MINUTES')
        disableConcurrentBuilds()
    }
    tools {
        maven 'mvn'
    }
    stages {
        stage('info') {
            steps {
                echo "Running job: ${env.JOB_NAME}\nbuild: ${env.BUILD_ID} - ${env.BUILD_URL}\nblue ocean: ${env.RUN_DISPLAY_URL}"
            }
        }
        stage('source') {
            steps {
                git 'https://github.com/tobantal/cb-test'
            }
        }
        stage('build') {
            steps {
                sh 'mvn -DskipTests=true clean install'
            }
        }
        stage('test') {
            steps {
                sh 'mvn test'
                //sh 'mvn -Dtest=ru/cb/app/validator/PersonValidatorTest test'
            }
        }
        /*
        stage('sonar') {
            steps {
                sh """mvn sonar:sonar \
                -Dsonar.host.url=http://172.17.0.3:9000 \
                -Dsonar.login=ed80a02aff2d3871ef5636965fba82b84aea0a0d"""
            }
        }
        */
        /*
        stage('sonarcloud') {
            steps {
                sh """mvn sonar:sonar \
                -Dsonar.host.url=https://sonarcloud.io \
                -Dsonar.login=xxxxxxxx"""
            }
        }
        */
    }
    post {
        always {
            deleteDir()
        }
    }
}
