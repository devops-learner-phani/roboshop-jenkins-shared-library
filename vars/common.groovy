def pipelineInit() {
  stage('Initiate repo') {
    sh 'rm -rf *'
    git branch: 'main', url: "https://github.com/devops-learner-phani/${COMPONENT}.git"
  }
}

def publishArtifacts() {
  stage("Prepare Artifacts") {
    if (env.APP_TYPE == "nodejs") {
      sh """
        zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js
      """
    }
    if (env.APP_TYPE == "python") {
      sh """
        zip -r ${COMPONENT}-${TAG_NAME}.zip *.py requirements.txt ${COMPONENT}.ini
      """
    }
    if (env.APP_TYPE == "maven") {
      sh """
        mv target/${COMPONENT}-1.0.jar ${COMPONENT}.jar
        zip -r ${COMPONENT}-${TAG_NAME}.zip ${COMPONENT}.jar 
      """
    }
    if (env.APP_TYPE == "golang") {
      sh """
        zip -r ${COMPONENT}-${TAG_NAME}.zip *
      """
    }
    if (env.APP_TYPE == "nginx") {
      sh """
        cd static
        zip -r ../${COMPONENT}-${TAG_NAME}.zip * 
      """
    }
  }
  stage("Push artifacts to Nexus") {
    withCredentials([usernamePassword(credentialsId: 'nexus', passwordVariable: 'pass', usernameVariable: 'user')]) {
      sh """
        curl -v -u ${user}:${pass} --upload-file ${COMPONENT}-${TAG_NAME}.zip  http://nexus.roboshop.internal:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
      """
    }
  }
}