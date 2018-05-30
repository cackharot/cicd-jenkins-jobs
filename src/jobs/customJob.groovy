import org.cackharot.jenkins.dsl.custom.JobDslParser

File dir = new File('.')
println("script directory: ${dir.absolutePath}")

new JobDslParser().load("${dir.absolutePath}/src/jobs/custom").build(this)