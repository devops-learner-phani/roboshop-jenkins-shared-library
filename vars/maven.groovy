def call() {
  node() {
    common.pipelineInit()
    stage('Download dependencies') {
      sh 'mvn clean package'
    }
    common.codeChecks()
    if (env.BRANCH_NAME == env.TAG_NAME)
    {
      common.publishArtifacts()
    }
  }
}