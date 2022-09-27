def call() {
  node() {
    common.pipelineInit()
    stage('Download dependencies') {
      sh 'mvn clean package'
    }
    if (env.BRANCH_NAME == env.TAG_NAME)
    {
      common.publishArtifacts()
    }
  }
}