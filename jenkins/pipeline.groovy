pipeline {
    agent any

    stages {
        stage('Prepare') {
            steps {
                echo bool
                echo choice
                echo multiline
                echo string
                sh """
                    curl -X PUT -H 'Content-Type:application/json' ${cbUrl}/rest/item -u ${cbUser}:${cbPwd} -d '{"uri":"/item/${taskId}","buildInfo":"[show|${JENKINS_URL}blue/organizations/jenkins/codebeamer/detail/codebeamer/${BUILD_NUMBER}/pipeline]","reportInfo":"[show|${BUILD_URL}testReport]"}'
                """
            }
        }
        stage('Build') {
            steps {
                echo "do something build here"
                sleep 5
            }
        }
        stage('Test') {
            steps {
                echo "mock test"
                
                writeFile file: 'test_result.xml', text: '''
                 <testsuite name='sweet' time='200.0'>
                    <testcase classname='Klazz' name='test1' time='198.0'>
                      <error message='failure'/>
                    </testcase>
                    <testcase classname='Klazz' name='test2' time='2.0'/>
                    <testcase classname='other.Klazz' name='test3'>
                    <skipped message='Not actually run.'/>
                    </testcase>
                  </testsuite>
                '''
                
                junit 'test_result.xml'
            }
        }
        stage('Complete') {
            steps {
                sh """
                    curl -X PUT -H 'Content-Type:application/json' ${cbUrl}/rest/item -u ${cbUser}:${cbPwd} -d '{"uri":"/item/${taskId}","status":{"id":7}}'
                """
            }
        }
    }
}
