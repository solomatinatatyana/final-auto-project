#!/usr/bin/env groovy
import hudson.tasks.test.AbstractTestResultAction
import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*

node {

    RECIPIENT = "tokio9507@gmail.com"

    try {
        stage('Checkout'){
            properties([[$class: 'JiraProjectProperty'],
                        [$class: 'GithubProjectProperty', displayName: '', projectUrlStr: 'https://github.com/solomatinatatyana/final-auto-project'],
                        [$class: 'RebuildSettings', autoRebuild: false, rebuildDisabled: false],
                        buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '365', numToKeepStr: '36')),
                        parameters(
                                [string(defaultValue: 'CHROME', description: '', name: 'BROWSER', trim: false),
                                 gitParameter(branch: '', branchFilter: '.*', defaultValue: 'origin/master', description: '', name: 'BRANCH', quickFilterEnabled: false, selectedValue: 'NONE', sortMode: 'NONE', tagFilter: '*', type: 'PT_BRANCH')
                                ]),
                        pipelineTriggers([githubPush(), cron('H 1 * * *')
                        ])
            ])
            checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '62b53291-36d6-4ccb-95cf-efa68b08f788', url: 'https://github.com/solomatinatatyana/final-auto-project']]])
        }

        stage('Run Tests') {
            withMaven(jdk: 'jdk_1.8.0_251', maven: 'maven') {
                bat 'echo Run'
                bat label: '', script: 'mvn clean test -Dbrowser='+BROWSER
            }
        }
    }catch(ex){
        currentBuild.result = 'FAILURE'
    }finally{
        stage('Generate Allure Reports') {
            allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
            ])
        }

        stage ('SendMailReport'){
            echo "Sending e-mail"
            BUILD_DURATION = "${currentBuild.durationString.replace(' and counting', '')}"
            emailext body: ''' 

<!DOCTYPE html>
<html>
<body>
<table style="width=100%" border=1>
                <tr>
                               <td BGCOLOR="#A4A4A4" width="20%"><FONT COLOR=white FACE="Geneva, Arial" SIZE=2><b>Build Number</font></b></td>
                               <td>$BUILD_NUMBER</td>
                </tr>
    <tr>
                               <td BGCOLOR="#A4A4A4" width="20%"><FONT COLOR=white FACE="Geneva, Arial" SIZE=2><b>Branch</td>
                               <td>$BRANCH</td>
                </tr>
    <tr>
                               <td BGCOLOR="#A4A4A4" width="20%"><FONT COLOR=white FACE="Geneva, Arial" SIZE=2><b>Browser</td>
                               <td>$browser</td>
                </tr>
    <tr>
                               <td BGCOLOR="#A4A4A4" width="20%"><FONT COLOR=white FACE="Geneva, Arial" SIZE=2><b>Status</td>
                               <td>$BUILD_STATUS</td>
                </tr>
                               <td BGCOLOR="#0099CC" width="20%"><FONT COLOR=white FACE="Geneva, Arial" SIZE=2><b>Total tests count</font></b></td>
                               <td BGCOLOR="#FFFFFF"><FONT COLOR=#0099CC FACE="Geneva, Arial" SIZE=2><b>${TEST_COUNTS,var="total"}</font></b></td>      
                </tr>
                <tr>
                               <td BGCOLOR="#04B431" width="20%"><FONT COLOR=white FACE="Geneva, Arial" SIZE=2><b>Success</font></b></td>
        <td BGCOLOR="#FFFFFF"><FONT COLOR=#04B431 FACE="Geneva, Arial" SIZE=2><b>${TEST_COUNTS,var="pass"}</font></b></td>       
                </tr>
                <tr>
                               <td BGCOLOR="#DF3A01" width="20%"><FONT COLOR=white FACE="Geneva, Arial" SIZE=2><b>Unstable tests</b></font></td>
                               <td BGCOLOR="#FFFFFF"><FONT COLOR=#DF3A01 FACE="Geneva, Arial" SIZE=2><b>${TEST_COUNTS,var="fail"}</font></b></td>       
                </tr>   
                <tr>
               <td BGCOLOR="#A4A4A4" width="20%"><FONT COLOR=white FACE="Geneva, Arial" SIZE=2><b>Skipped tests</font></b></td>
               <td BGCOLOR="#FFFFFF"><FONT COLOR=#A4A4A4 FACE="Geneva, Arial" SIZE=2><b>${TEST_COUNTS,var="skip"}</font></b></td>       
        </tr>
        <tr>
                               <td BGCOLOR="#A4A4A4" width="20%"><FONT COLOR=white FACE="Geneva, Arial" SIZE=2><b>Duration</b></td>
                               <td>'''+ BUILD_DURATION +'''</td>
                </tr>
                <tr>
                               <td BGCOLOR="#A4A4A4" width="20%"><FONT COLOR=white FACE="Geneva, Arial" SIZE=2><b>Link to report</b></td>
                               <td>${BUILD_URL}allure</td>
                </tr>  
</table>
</html>
</body>''',
                    mimeType: 'text/html',
                    subject: "Autotests OTUS, on browser ${BROWSER} build number: ${BUILD_NUMBER}",
                    to: "${RECIPIENT}",
                    replyTo: "${RECIPIENT}"
        }
        stage('Send Slack Notification Report'){
            notifySlack(currentBuild.currentResult)
        }
    }
}

def notifySlack(String buildStatus = 'STARTED') {
    def total
    def failed
    def skipped
    def passed
    AbstractTestResultAction testResultAction = currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
    if (testResultAction != null){
        total = testResultAction.totalCount
        failed = testResultAction.failCount
        skipped = testResultAction.skipCount
        passed = total - failed - skipped
    }else {
        total = "not found tests"
        failed = "not found tests"
        skipped = "not found tests"
        passed = "not found tests"
    }
    buildStatus = buildStatus ?: 'SUCCESS'
    def color
    if (buildStatus == 'STARTED') {
        color = '#D4DADF'
    } else if (buildStatus == 'SUCCESS') {
        color = '#BDFFC3'
    } else if (buildStatus == 'UNSTABLE') {
        color = '#FFFE89'
    } else {
        color = '#FF9FA1'
    }
    def msg = "${buildStatus}: `${env.JOB_NAME}` #${env.BUILD_NUMBER}:\n${env.BUILD_URL}allure \n" +
    "Branch: $BRANCH \n" +
    "Browser: $browser \n" +
    "Total: ${total}\n" +
    "Passed: ${passed}\n" +
    "Failed: ${failed}\n" +
    "Skipped: ${skipped}\n" +
    "Duration: $BUILD_DURATION\n"
    slackSend (channel: 'solomka_jenkins', color: color, message: msg, teamDomain: 'otus-qa', tokenCredentialId: 'solomka_token')
    //slackSend(color: color, message: msg)
}