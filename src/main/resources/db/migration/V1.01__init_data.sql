-- Create table
CREATE TABLE test_data (
                           id SERIAL PRIMARY KEY ,
                           value BOOLEAN DEFAULT FALSE
);

-- Insert data
INSERT INTO test_data (id, value) VALUES (0, true);
