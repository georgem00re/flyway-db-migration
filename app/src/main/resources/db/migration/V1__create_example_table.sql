
CREATE EXTENSION IF NOT EXISTS "uuid-ossp"; -- This allows us to use the uuid_generate_v4 function.

CREATE TABLE IF NOT EXISTS example (
    id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY
);
