def pipelineInit() {
  stage('Initiate repo') {
    sh 'rm -rf *'
    git branch: 'main', url: "https://github.com/devops-learner-phani/${COMPONENT}.git"
  }
}

def publishArtifacts() {
  stage('Prepare Artifacts') {
    if (env.APP_TYPE == "nodejs")
    sh """
      zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js
    """
  }
  stage('Push artifacts to Nexus') {
    withCredentials([usernamePassword(credentialsId: 'nexus', passwordVariable: 'pass', usernameVariable: 'user')]) {
      sh """
        curl -v -u ${user}:${pass} --upload-file ${COMPONENT}-${TAG_NAME}.zip  http://nexus.roboshop.internal:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
      """
    }
  }
}