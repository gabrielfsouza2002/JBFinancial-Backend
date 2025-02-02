-- src/main/resources/db/migration/V5__alter-base-id-to-uuid.sql

-- Drop the existing primary key constraint
ALTER TABLE base DROP CONSTRAINT base_pkey;

-- Drop the default value for the id column
ALTER TABLE base ALTER COLUMN id DROP DEFAULT;

-- Alter the column type to UUID
ALTER TABLE base ALTER COLUMN id TYPE UUID USING (uuid_generate_v4());

-- Set a new default value for the id column
ALTER TABLE base ALTER COLUMN id SET DEFAULT uuid_generate_v4();

-- Add the primary key constraint back
ALTER TABLE base ADD CONSTRAINT base_pkey PRIMARY KEY (id);