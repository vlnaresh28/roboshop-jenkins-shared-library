def compile() {
  if(app_lang == "nodejs") {
    sh 'npm install'
  }
  if(app_lang == "maven") {
    sh 'mvn package'
  }
}

def testcases() {
  // npm test
  // mvn test
  // python -m unittests
  // go test
  sh 'echo OK'
}


def codequality() {
  withAWSParameterStore(credentialsId: 'PARAM', naming: 'absolute', path: '/sonarqube', recursive: true, regionName: 'us-east-1') {
        // sh 'sonar-scanner -Dsonar.host.url=http://172.31.1.126:9000 -Dsonar.login=${SONARQUBE_USER} -Dsonar.password=${SONARQUBE_PASS} -Dsonar.projectKey=${component} ${sonar_extra_opts} -Dsonar.qualitygate.wait=true'
        sh 'echo OK'
    } 
}

def prepareArtifacts() {
//  sh 'echo ${TAG_NAME} >VERSION'
//  if (app_lang == "maven") {
//    sh 'zip -r ${component}-${TAG_NAME}.zip ${component}.jar schema VERSION'
//  } else {
//    sh 'zip -r ${component}-${TAG_NAME}.zip * -x Jenkinsfile'
//  }
  sh 'docker build -t 739561048503.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME} .'
}

def artifactUpload() {
//  env.NEXUS_USER = sh ( script: 'aws ssm get-parameter --name prod.nexus.user --with-decryption | jq .Parameter.Value | xargs', returnStdout: true ).trim()
//  env.NEXUS_PASS = sh ( script: 'aws ssm get-parameter --name prod.nexus.pass --with-decryption | jq .Parameter.Value | xargs', returnStdout: true ).trim()
//  wrap([$class: 'MaskPasswordsBuildWrapper',
//        varPasswordPairs: [[password: NEXUS_PASS],[password: NEXUS_USER]]]) {
//    sh 'echo ${TAG_NAME} >VERSION'
//    sh 'curl -v -u ${NEXUS_USER}:${NEXUS_PASS} --upload-file ${component}-${TAG_NAME}.zip http://172.31.11.210:8081/repository/${component}/${component}-${TAG_NAME}.zip'
//
//  }
  sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 739561048503.dkr.ecr.us-east-1.amazonaws.com'
  sh 'docker push 739561048503.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME}'
}









