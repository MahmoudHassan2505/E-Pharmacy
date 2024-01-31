INSERT INTO users(
    username, password, enabled, phone)
VALUES ('admin', '{noop}admin', 1, 01098400483),('mahmoud', '{noop}mahmoud', 1, 01098400483)
    ON CONFLICT (username) DO NOTHING;

INSERT INTO authorities(
    username, authority)
VALUES ('admin', 'ROLE_ADMIN'),('admin', 'ROLE_EMPLOYEE'),('mahmoud','ROLE_EMPLOYEE')
ON CONFLICT (username,authority) DO NOTHING;

-- INSERT INTO supplier(name)
-- VALUES ('South Egypt Drug Industries Co (SEDICO)'),
--        ('Viatris Egypt'),
--        ('ابن سينا فارما'),
--        ('اخناتون للتجاره والتوزيع'),
--        ('الشرق الاوسط للكيماويات'),
--        ('الشركه المصريه لتجاره الادويه'),
--        ('العربيه المتحده للادويه'),
--        ('المتحده للصيادله'),
--        ('رامكو فارم لتجاره الادويه'),
--        ('سوفيكو فارم'),
--        ('فارما اوفر سيز'),
--        ('شركه ليمتليس للمستحضرات الصيدليه و المكملات الغائيه'),
--        ('مينا فارم'),
--        ('مالتي فارما للادويه'),
--        ('The Egyption company for blood transfusion services'),
--        ('ALLMED Middle East'),
--        ('Biolinx');
--
-- INSERT INTO medicine_category(name)
-- VALUES ('اقراص'),
--        ('امبولات'),
--        ('قطرات'),
--        ('مراهم'),
--        ('كريمات'),
--        ('شراب'),
--        ('مستلزمات طبيه');



