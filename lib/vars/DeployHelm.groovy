#!/usr/bin/env groovy
import com.Constants

def call(String branch_ng, String tag_ng, String branch_nd, String tag_nd) {
    sh("scp -o StrictHostKeyChecking=no -r helm/ ubuntu@${Constants.IP_K8S}:~/")
    withCredentials([file(credentialsId: 'deployhelm', variable: 'deployhelm')]) {
        sh("ssh ubuntu@${Constants.IP_K8S} 'cd helm; \
            sed -i.bak \"s/%BRNG%/${branch_ng}/; s/%TAGNG%/${tag_ng}/; \
                s/%BRND%/${branch_nd}/; s/%TAGND%/${tag_nd}/\" values.yaml; \
            KUBECONFIG=\"/home/ubuntu/helm/kubernetes-deploy-helm-kube.conf\" \
            echo $KUBECONFIG \
            helm upgrade --install --create-namespace -n my-project --kubeconfig \
                kubernetes-deploy-helm-kube.conf --kube-as-user deploy-helm test .;'")
    }
}