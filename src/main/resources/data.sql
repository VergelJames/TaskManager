INSERT INTO users (
  uuid,
  first_name,
  middle_name,
  last_name,
  email,
  phone_number
) VALUES (
  '11111111-1111-1111-1111-111111111111',
  'Alice',
  'Marie',
  'Smith',
  'alice.smith@example.com',
  5551001
);

INSERT INTO users (
  uuid,
  first_name,
  middle_name,
  last_name,
  email,
  phone_number
) VALUES (
  '22222222-2222-2222-2222-222222222222',
  'Bob',
  'James',
  'Jones',
  'bob.jones@example.com',
  5551002
);


INSERT INTO task (
  uuid,
  title,
  description,
  due_on_date,
  owner_uuid,
  status,
  priority
) VALUES (
  'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
  'Sprint planning',
  'Prepare agenda and assign tasks for next sprint.',
  '2026-06-01',
  '11111111-1111-1111-1111-111111111111',
  'COM',
  'LO'
);

INSERT INTO task (
  uuid,
  title,
  description,
  due_on_date,
  owner_uuid,
  status,
  priority
) VALUES (
  'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
  'Code review',
  'Review pull requests for the authentication module.',
  '2026-06-03',
  '11111111-1111-1111-1111-111111111111',
  'INP',
  'HI'
);

INSERT INTO task (
  uuid,
  title,
  description,
  due_on_date,
  owner_uuid,
  status,
  priority
) VALUES (
  'cccccccc-cccc-cccc-cccc-cccccccccccc',
  'Release announcement',
  'Draft the release notes and notify stakeholders.',
  '2026-06-05',
  '22222222-2222-2222-2222-222222222222',
  'CLO',
  'MI'
);