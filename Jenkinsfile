pipeline {
    agent any
    stages {
        stage('Build-TAMASDesktop') {
            steps {
                checkout scm
                withEnv( ["JAVA_HOME=${tool 'JDK-1.8-8u121'}"] ) {
                    withEnv( ["ANT_HOME=${tool 'Ant-1.10.1'}"] ) {
                        sh "sudo rm -rf /home/ecccd/web/TAMASWeb"
                        sh "sudo cp -R TAMASWeb /home/ecccd/web/TAMASWeb"
                        sh "sudo chmod 777 /home/ecccd/web/TAMASWeb"
                        sh "sudo chown -R web1:client1 /home/ecccd/web/TAMASWeb"
                        sh "cd ./TAMASWeb; phpunit --bootstrap ./autoload.php test/TestAuthentication"
                        sh "cd ./TAMASWeb; phpunit --bootstrap ./autoload.php test/TestPersistence"
                        sh "cd ./TAMASWeb; phpunit --bootstrap ./autoload.php test/TestController"
                        sh "cd ./TAMASWeb; phpunit --bootstrap ./autoload.php test/TestModel"
                        sh "cd ./TAMASDesktop; ant exportjar"
                        sh "cd ./TAMASDesktop; ant test"
                    }
                }
                archiveArtifacts artifacts: '**/bin/*.jar', fingerprint: true
                archiveArtifacts artifacts: '**/coverage-report/ant-test/*.xml', fingerprint: true
            }
        }
    }
}
