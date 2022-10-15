def call() {
  node() {

    stage(Terraform INIT) {
      sh 'terraform init'
    }

    stage(terraform plan) {
      sh 'terraform plan'
    }

    stage(terraform apply) {
      sh 'terraform apply -auto-approve'
    }
  }
}