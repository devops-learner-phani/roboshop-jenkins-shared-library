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
      zip -r ${COMPONENT}.zip node_modules server.js
    """
  }
}