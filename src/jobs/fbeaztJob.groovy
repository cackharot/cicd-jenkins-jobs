String basePath = 'fbeazt'
String repo = 'cackharot/fbeazt'

folder(basePath) {
    description 'For fbeazt project build and deploy jobs'
}

job("$basePath/build") {
    scm {
        github repo
    }
    triggers {
        scm 'H/5 * * * *'
    }
    steps {
        println 'Building'
    }
}

job("$basePath/deploy") {
    parameters {
        stringParam 'host'
    }
    steps {
        shell 'echo $host'
    }
}
