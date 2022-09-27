def pipelineInit() {
  stage('Initiate repo') {
    sh 'rm -rf *'
    git branch: 'main', url: "https://github.com/devops-learner-phani/${COMPONENT}.git"
  }
}

def publishArtifacts() {
  env.ENV="dev"
  stage("Prepare Artifacts") {
    if (env.APP_TYPE == "nodejs") {
      sh """
        zip -r ${ENV}-${COMPONENT}-${TAG_NAME}.zip node_modules server.js
      """
    }
    if (env.APP_TYPE == "python") {
      sh """
        zip -r ${ENV}-${COMPONENT}-${TAG_NAME}.zip *.py requirements.txt ${COMPONENT}.ini
      """
    }
    if (env.APP_TYPE == "maven") {
      sh """
        mv target/${COMPONENT}-1.0.jar ${COMPONENT}.jar
        zip -r ${ENV}-${COMPONENT}-${TAG_NAME}.zip ${COMPONENT}.jar 
      """
    }
    if (env.APP_TYPE == "golang") {
      sh """
        zip -r ${ENV}-${COMPONENT}-${TAG_NAME}.zip ${COMPONENT}
      """
    }
    if (env.APP_TYPE == "nginx") {
      sh """
        cd static
        zip -r ../${ENV}-${COMPONENT}-${TAG_NAME}.zip * 
      """
    }
  }
  stage("Push artifacts to Nexus") {
    withCredentials([usernamePassword(credentialsId: 'nexus', passwordVariable: 'pass', usernameVariable: 'user')]) {
      sh """
        curl -v -u ${user}:${pass} --upload-file ${ENV}-${COMPONENT}-${TAG_NAME}.zip  http://nexus.roboshop.internal:8081/repository/${COMPONENT}/${ENV}-${COMPONENT}-${TAG_NAME}.zip
      """
    }
  }
  stage("Deploy-to-any-dev") {
    sh """
      build job: 'Deploy-to-any-dev', parameters: [string(name: 'COMPONENT', value: "${COMPONENT}"), string(name: 'ENV', value: "${ENV}"), string(name: 'APP_VERSION', value: "${TAG_NAME}")]
    """
  }
}

def codeChecks() {
  stage('unit test and Quality checks') {
    parallel([
        Qualitychecks: {
//          withCredentials([usernamePassword(credentialsId: 'sonar', passwordVariable: 'pass', usernameVariable: 'user')]) {
//            sh "sonar-scanner -Dsonar.projectKey=${COMPONENT} -Dsonar.host.url=http://172.31.6.103:9000 -Dsonar.login=${user} -Dsonar.password=${pass} ${EXTRA_OPTS}"
//            sh "sonar-quality-gate.sh ${user} ${pass} 172.31.6.103 ${COMPONENT}"
//          }
          echo "quality checks"
        },
        unitTests: {
          unitTests()
        }
    ])
  }
}

def unitTests() {
  stage("Unit tests") {
    if (env.APP_TYPE == "nodejs") {
      sh """
        #npm run test
        echo Run test cases
      """
    }
    if (env.APP_TYPE == "python") {
      sh """
        #python -m unittest
        echo Run test cases
      """
    }
    if (env.APP_TYPE == "maven") {
      sh """
        #mvn test
        echo Run test cases
      """
    }
    if (env.APP_TYPE == "nginx") {
      sh """
        #npm run test
        echo Run test cases
      """
    }
  }
}