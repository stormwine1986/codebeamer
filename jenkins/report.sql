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

-- change_master
CREATE TABLE change_master (
  id				char(50),
  project         	varchar(255),
  branch          	varchar(255),
  number			int8,			
  subject           varchar(1000),
  owner				varchar(255),
  feature_group		varchar(255),
  department		varchar(255),
  url				varchar(255),
  commit_message	varchar(1000),
  created_on		timestamp,
  last_updated		timestamp,
  open				boolean,
  status			varchar(255),
  origin_data		json,
  sizeInsertions    int8,
  sizeDeletions     int8,
  density			numeric,
  commit_type		varchar(255)
);

-- change_comment
CREATE TABLE change_comment (
  change_id			char(50),
  file				varchar(1000),
  line				int4,
  reviewer			varchar(255),
  message_origin	varchar(1000),
  message			varchar(1000),
  type				varchar(255),
  severity			varchar(255)
);

-- change_file
CREATE TABLE change_file (
  change_id			char(50),
  file				varchar(1000),
  type				varchar(255),
  insertions		int4,
  deletions			int4,
  create_on			timestamp
);
