import jenkins.model.*
import hudson.security.*
import org.jenkinsci.plugins.*
import hudson.model.FreeStyleProject
import hudson.plugins.git.*

def instance = Jenkins.getInstance()

def ldapRealm = new LDAPSecurityRealm(
  "ldap://openldap:389", // LDAP server
  "dc=example,dc=com", // root DN
  "ou=Users", // user search base
  "uid={0}", // user search filter
  null, // group search base
  null, // group search filter
  false, // disable email lookup
  null, // email address lookup
  null, // manager DN
  null, // manager password
  null, // user search base
  false, // inhibit infer root DN
  null, // display name attribute
  null, // mail address attribute
  "(&(uid={0}))", // extra user search filter
  null, // group membership filter
  null, // group membership attribute
  null, // group search filter
  null, // manager password file
  null, // group membership attribute
  null, // user search scope
  null // cache settings
)

instance.setSecurityRealm(ldapRealm)
instance.save()

// Create a new pipeline job for Node.js
def jobName = "NodeJS_Pipeline"
def job = instance.createProject(hudson.model.FreeStyleProject, jobName)
def scm = new GitSCM("https://github.com/YOUR_REPO/nodejs-app.git") // Replace YOUR_REPO with your repository URL
job.setScm(scm)
job.addProperty(new hudson.model.ParametersDefinitionProperty(
  new hudson.model.StringParameterDefinition("BRANCH", "main", "Branch to build")
))
job.save()

