
if(beforeEvent) return

// def sout = new StringBuilder(), serr = new StringBuilder()

logger.info("####################################")
def proc = '../01.sh'.execute()
logger.info("------------------------------------")

//proc.consumeProcessOutput(sout, serr)
//proc.waitForOrKill(1000)
//println "out> $sout\nerr> $serr"
