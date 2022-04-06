#!/usr/bin/env groovy
import com.Constants

def call(String branch_dep, String tag_dep, String service_dep) {
  // if ("${BRANCH_DEV}" == 'develop') {
  //     this.script.sh('ssh ubuntu@${IP_K8S} \
  //     """cd repos/project_lib_deploy/yaml; \
  //     export BRANCH=${branch}; \
  //     export TAG=${tag}; \
  //     export SERVICE=${service}; \
  //     kubectl create namespace ${branch}; \
  //     kubectl apply -f issuer.yaml; \
  //     envsubst < ingress.yaml | kubectl apply -f -; \
  //     kubectl delete -n ${branch} secret regcred --ignore-not-found; \
  //     kubectl create secret docker-registry regcred \
  //             --docker-server=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com \
  //             --docker-username=AWS \
  //             --docker-password=$(aws ecr get-login-password --region ${AWS_REGION}) \
  //             --namespace=${branch}; \
  //     envsubst < service-${service}.yaml | kubectl apply -f -; \
  //     kubectl delete deploy deploy-${service} -n ${branch}; \
  //     envsubst < deploy-${service}.yaml | kubectl apply -f -;"""')
  // } else {
      //PASS=sh("aws ecr get-login-password --region ${Constants.AWS_REGION}")
      //echo "${PASS}"
      //String PASS=""
      PASS=sh(script: "aws ecr get-login-password --region ${Constants.AWS_REGION}",
        returnStdout: true).trim()
      sh("ssh ubuntu@${Constants.IP_K8S} \
        'cd repos/project_lib_deploy/yaml; \
        kubectl delete -n ${branch_dep} secret regcred --ignore-not-found; \
        export BRANCH=${branch_dep}; \
        export TAG=${tag_dep}; \
        export SERVICE=${service_dep}; \
        kubectl create namespace ${branch_dep}; \
        kubectl apply -f issuer.yaml; \
        envsubst < ingress.yaml | kubectl apply -f -; \
        kubectl create secret docker-registry regcred -n ${branch_dep} \
                --docker-username=AWS \
                --docker-server=${Constants.AWS_ACCOUNT_ID}.dkr.ecr.${Constants.AWS_REGION}.amazonaws.com \
                --docker-password=${PASS}; \
        envsubst < service-${service_dep}.yaml | kubectl apply -f -; \
        kubectl delete deploy deploy-${service_dep} -n ${branch_dep}; \
        envsubst < deploy-${service_dep}.yaml | kubectl apply -f -;'")
   //}
}