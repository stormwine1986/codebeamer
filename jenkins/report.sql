-- statistics per build
select job,build,
	count(1) as total, 
	sum(case when errordetails notnull then 1 end) as failcount,
	sum(case when skipped notnull then 1 end) as skippedcount,
	sum(case when errordetails is null and skipped is null then 1 end) as successcount
from caseresults c group by (job,build)

-- statistics per case
select suite,package,classname,testname, 
	count(1) as total,
	sum(case when errordetails notnull then 1 end) as failcount,
	sum(case when skipped notnull then 1 end) as skippedcount,
	sum(case when errordetails is null and skipped is null then 1 end) as successcount
from caseresults c group by (suite,package,classname,testname)
