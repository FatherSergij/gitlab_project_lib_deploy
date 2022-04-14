#!/usr/bin/env groovy
import com.Constants

def call(String branch_ng, String tag_ng, String branch_nd, String tag_nd) {
    build job: 'Job_deploy_helm', parameters: [string(name: 'BranchRun_ng', value: "${branch_ng}"), 
                  string(name: 'ImageTag_ng', value: "${tag_ng}"),
                  string(name: 'BranchRun_nd', value: "${branch_nd}"),
                  string(name: 'ImageTag_nd', value: "${tag_nd}")]
}