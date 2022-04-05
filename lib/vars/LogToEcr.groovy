import com.Constants

def call() {
    sh("aws ecr get-login-password --region Constants.${AWS_REGION} | docker login --username AWS \
      --password-stdin Constants.${AWS_ACCOUNT_ID}.dkr.ecr.Constants.${AWS_REGION}.amazonaws.com")
}