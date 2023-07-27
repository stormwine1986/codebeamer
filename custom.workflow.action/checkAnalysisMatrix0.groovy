// check anaysis matrix after 根因假设
//	1.	"Anaysis Matrix" MUST contains one line
//	2.	"根因假设" and "提交人" MUST be filled for each line

if(!beforeEvent) return

import com.intland.codebeamer.event.util.VetoException

// Defined Constant
def FIELD_ID_ANAYSIS_MATRIX = 0	// Field Id of "Anaysis Matrix" which in "Defect"
def COL_IDX_ASSUME = 0			// Index of column named "根因假设"
def COL_IDX_SUBMITTER = 1		// Index of column named "提交人"

def table0 = subject.getTable(FIELD_ID_ANAYSIS_MATRIX)

if(table0.size()==0) throw new VetoException("根因假设和提交人必须填写。")

def pass = true
table0.each{
	it ->
	def keys = it.keySet()
	if(!keys.contains(COL_IDX_ASSUME)||!keys.contains(COL_IDX_SUBMITTER)){
		pass = false
	}
}

if(!pass) throw new VetoException("根因假设和提交人必须填写。")
