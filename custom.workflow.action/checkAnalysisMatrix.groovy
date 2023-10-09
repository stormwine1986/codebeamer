// check anaysis matrix
// 	1. 	"Verify Result" and "Verfier" MUST be filled for each line

if(!beforeEvent) return

import com.intland.codebeamer.event.util.VetoException

// Defined Constants
def FIELD_ID_ANAYSIS_MATRIX = 0	// Field Id of "Anaysis Matrix" which in "Defect"
def COL_IDX_VERIFY_RESULT = 2	// Index of column named "验证结果"
def COL_IDX_VERIFIER = 3		// Index of column named "验证人"

def table0 = subject.getTable(FIELD_ID_ANAYSIS_MATRIX)
def pass = true
table0.each{
	it ->
	def keys = it.keySet()
	if(!keys.contains(COL_IDX_VERIFY_RESULT)||!keys.contains(COL_IDX_VERIFIER)){
		pass = false
	}
}

if(!pass) throw new VetoException("验证结果和验证人必须提交")