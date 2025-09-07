-- First, drop the existing check constraint (H2 auto-generates constraint names)
-- We'll drop all possible constraint names
ALTER TABLE issues DROP CONSTRAINT IF EXISTS issues_status_check;
ALTER TABLE issues DROP CONSTRAINT IF EXISTS CONSTRAINT_814A;
ALTER TABLE issues DROP CONSTRAINT IF EXISTS SYS_CT_10094;

-- IMPORTANT: Update the data FIRST, before adding the new constraint
UPDATE issues SET status = 'DONE' WHERE status = 'RESOLVED';

-- Now add new constraint with the updated values
ALTER TABLE issues ADD CONSTRAINT chk_issues_status_v2
    CHECK (status IN ('OPEN','IN_PROGRESS','DONE'));
