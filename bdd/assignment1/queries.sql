-- ------------------------------------------------------
-- NOTE: DO NOT REMOVE OR ALTER ANY LINE FROM THIS SCRIPT
-- ------------------------------------------------------

select 'Query 00' as '';
-- Show execution context
select current_date(), current_time(), user(), database();
-- Conform to standard group by constructs
set session sql_mode = 'ONLY_FULL_GROUP_BY';

-- Write the SQL queries that return the information below:
-- Ecrire les requêtes SQL retournant les informations ci-dessous:

select 'Query 01' as '';
-- The countries of residence the supplier had to ship products to in 2014
-- Les pays de résidence où le fournisseur a dû envoyer des produits en 2014
select distinct residence
from customers natural join orders
where extract(year from odate) = 2014
and residence is not null;

select 'Query 02' as '';
-- For each known country of origin, its name, the number of products from that country, their lowest price, their highest price
-- Pour chaque pays d'orgine connu, son nom, le nombre de produits de ce pays, leur plus bas prix,
-- leur plus haut prix
select origin o,
       count(origin) products,
       (select min(price) from products where origin = o) lowest_price,
       (select max(price) from products where origin = o) highest_price
from products
where origin is not null
group by origin;

select 'Query 03' as '';
-- The customers who ordered in 2014 all the products (at least) that the customers named 'Smith' ordered in 2013
-- Les clients ayant commandé en 2014 tous les produits (au moins) commandés
-- par les clients nommés 'Smith' en 2013
select *
from customers c
where not exists (
    select pid
    from customers natural join orders
    where pid not in (
        select distinct pid
        from orders
        where extract(year from odate) = 2014
        and c.cid = cid
    )
    and cname = 'Smith'
    and extract(year from odate) = 2013
);

select 'Query 04' as '';
-- For each customer and each product, the customer's name, the product's name, the total amount ordered by the customer for that product,
-- sorted by customer name (alphabetical order), then by total amount ordered (highest value first), then by product id (ascending order)
-- Par client et par produit, le nom du client, le nom du produit,
-- le montant total de ce produit commandé par le client, 
-- trié par nom de client (ordre alphabétique), puis par montant total
-- commandé (plus grance valeur d'abord), puis par id de produit (croissant)
select cname,
       pname,
       sum(quantity * price) as montant
from customers natural join orders natural join products
group by cid, pid, cname, pname
order by cname, montant desc, pid;

select 'Query 05' as '';
-- The customers who only ordered products originating from their country
-- Les clients n'ayant commandé que des produits provenant de leur pays
select *
from customers
where cid not in (
    select cid
    from customers natural join orders natural join products
    where residence <> origin
)
and residence is not null;

select 'Query 06' as '';
-- The customers who ordered only products originating from foreign countries 
-- Les clients n'ayant commandé que des produits provenant de pays étrangers
select *
from customers
where cid not in (
    select cid
    from customers natural join orders natural join products
    where residence = origin
)
and residence is not null;

select 'Query 07' as '';
-- The difference between 'USA' residents' per-order average quantity and 'France' residents' (USA - France)
-- La différence entre quantité moyenne par commande des clients résidant
-- aux 'USA' et celle des clients résidant en 'France' (USA - France)
select us.average - fr.average
from (
	select avg(quantity) as average
    from customers natural join orders
    where residence = 'USA'
) us cross join (
	select avg(quantity) as average
    from customers natural join orders
    where residence = 'France'
) fr;

select 'Query 08' as '';
-- The products ordered throughout 2014, i.e. ordered each month of that year
-- Les produits commandés tout au long de 2014, i.e. commandés chaque mois de cette année
select pid, pname, price, origin
from (
    select pid, pname, price, origin, count(month) counter
    from (
        select pid, pname, price, origin, extract(month from odate) as month
        from products natural join orders
        where extract(year from odate) = 2014
        group by pid, pname, price, origin, month
    ) t1
    group by pid, pname, price, origin
) t2
where counter = 12;

select 'Query 09' as '';
-- The customers who ordered all the products that cost less than $5
-- Les clients ayant commandé tous les produits de moins de $5
select cid, cname, residence
from (
    select cid, cname, residence, count(distinct pid) c
    from customers natural join orders natural join products
    where price < 5
    group by cid, cname, residence
) t
where c = (
    select count(distinct pid)
    from products
    where price < 5
);

select 'Query 10' as '';
-- The customers who ordered the greatest number of common products. Display 3 columns: cname1, cname2, number of common products, with cname1 < cname2
-- Les clients ayant commandé le grand nombre de produits commums. Afficher 3
-- colonnes : cname1, cname2, nombre de produits communs, avec cname1 < cname2
select t1.cname as cname1,
	   t2.cname as cname2,
       count(distinct t1.pid) as common
from (
    select *
    from customers natural join orders
) t1 inner join (
	select *
    from customers natural join orders
) t2 on t1.pid = t2.pid
where t1.cname < t2.cname
group by t1.cid, t2.cid, t1.cname, t2.cname
having common = (
	select max(common)
    from (
        select t1.cname as cname1,
               t2.cname as cname2,
               count(distinct t1.pid) as common
        from (
            select *
            from customers natural join orders
        ) t1 inner join (
            select *
            from customers natural join orders
        ) t2 on t1.pid = t2.pid
        where t1.cid <> t2.cid
        and t1.cname < t2.cname
        group by t1.cid, t2.cid, t1.cname, t2.cname
    ) t3
);

select 'Query 11' as '';
-- The customers who ordered the largest number of products
-- Les clients ayant commandé le plus grand nombre de produits
select cid, cname, residence
from (
    select cid, cname, residence, sum(distinct pid) qty
    from customers natural join orders
    group by cid, cname, residence
) t1
where qty = (
	select max(qty)
    from (
        select cid, cname, residence, sum(distinct pid) qty
        from customers natural join orders
        group by cid, cname, residence
    ) t2
);

select 'Query 12' as '';
-- The products ordered by all the customers living in 'France'
-- Les produits commandés par tous les clients vivant en 'France'
select *
from products
where pid in (
    select pid
    from (
        select pid, count(pid) c
        from (
            select cid, pid
            from customers natural join orders
            where residence = 'France'
        ) t1
        group by pid
    ) t4
    where c = (
        select count(*)
        from (
            select count(cid)
            from (
                select cid, pid
                from customers natural join orders
                where residence = 'France'
            ) t2
            group by cid
        ) t3
    )
);

select 'Query 13' as '';
-- The customers who live in the same country customers named 'Smith' live in (customers 'Smith' not shown in the result)
-- Les clients résidant dans les mêmes pays que les clients nommés 'Smith'
-- (en excluant les Smith de la liste affichée)
select *
from customers
where residence in (select residence from customers where cname = 'Smith')
and cname <> 'Smith';

select 'Query 14' as '';
-- The customers who ordered the largest total amount in 2014
-- Les clients ayant commandé pour le plus grand montant total sur 2014 
select cid, cname, residence
from (
	select cid, cname, residence, sum(price*quantity) total
    from customers natural join orders natural join products
    where extract(year from odate) = 2014
    group by cid, cname, residence
) t1
where total = (select max(total) from (
        select cid, cname, residence, sum(price*quantity) total
        from customers natural join orders natural join products
        where extract(year from odate) = 2014
        group by cid, cname, residence
	) t2
);

select 'Query 15' as '';
-- The products with the largest per-order average amount 
-- Les produits dont le montant moyen par commande est le plus élevé
select *
from products
where pid in (
    select pid
    from (
        select pid, avg(quantity * price) montant
        from orders natural join products
        group by pid
    ) t1
    where montant = (
        select max(montant)
        from (
            select pid, avg(quantity * price) montant
            from orders natural join products
            group by pid
        ) t2
    )
);

select 'Query 16' as '';
-- The products ordered by the customers living in 'USA'
-- Les produits commandés par les clients résidant aux 'USA'
select distinct pid, pname, price, origin
from customers natural join orders natural join products
where residence = 'USA';

select 'Query 17' as '';
-- The pairs of customers who ordered the same product en 2014, and that product. Display 3 columns: cname1, cname2, pname, with cname1 < cname2
-- Les paires de client ayant commandé le même produit en 2014, et ce produit.
-- Afficher 3 colonnes : cname1, cname2, pname, avec cname1 < cname2
select t1.cname as cname1,
	   t2.cname as cname2,
       t1.pname
from (
    select *
    from customers natural join orders natural join products
) t1 inner join (
	select *
    from customers natural join orders natural join products
) t2 on t1.pid = t2.pid
where t1.cname < t2.cname
and extract(year from t1.odate) = 2014
group by t1.cid, t2.cid, cname1, cname2;
-- CHECK ---------------------------------------------

select 'Query 18' as '';
-- The products whose price is greater than all products from 'India'
-- Les produits plus chers que tous les produits d'origine 'India'
select distinct *
from products
where price > all (
	select price
    from products
    where origin = 'India'
)
and (origin <> 'India' or origin is null);

select 'Query 19' as '';
-- The products ordered by the smallest number of customers (products never ordered are excluded)
-- Les produits commandés par le plus petit nombre de clients (les produits jamais commandés sont exclus)
select *
from products
where pid in (
    select pid
    from (
        select pid, count(distinct cid) c
        from orders
        group by pid
    ) t1
    where c = (
        select min(c)
        from (
            select pid, count(distinct cid) c
            from orders
            group by pid
        ) t2
    )
);

select 'Query 20' as '';
-- For all countries listed in tables products or customers, including unknown countries: the name of the country, the number of customers living in this country, the number of products originating from that country
-- Pour chaque pays listé dans les tables products ou customers, y compris les pays
-- inconnus : le nom du pays, le nombre de clients résidant dans ce pays, le nombre de produits provenant de ce pays 
select c1 as country, count(distinct cid), count(distinct pid)
from (
    select t1.country as c1, t1.cid, t2.pid
    from (
        select *
        from (
            select origin as country
            from products
            union
            select residence as country
            from customers
        ) countries left join customers on country = residence
        union
        select *
        from (
            select origin as country
            from products
            union
            select residence as country
            from customers
        ) countries right join customers on country = residence
    ) t1 left join (
        select *
        from (
            select origin as country
            from products
            union
            select residence as country
            from customers
        ) countries left join products on country = origin
        union
        select *
        from (
            select origin as country
            from products
            union
            select residence as country
            from customers
        ) countries right join products on country = origin
    ) t2 on t1.country = t2.country
    union
    select t3.country as c2, t3.cid, t4.pid
    from (
        select *
        from (
            select origin as country
            from products
            union
            select residence as country
            from customers
        ) countries left join customers on country = residence
        union
        select *
        from (
            select origin as country
            from products
            union
            select residence as country
            from customers
        ) countries right join customers on country = residence
    ) t3 right join (
        select *
        from (
            select origin as country
            from products
            union
            select residence as country
            from customers
        ) countries left join products on country = origin
        union
        select *
        from (
            select origin as country
            from products
            union
            select residence as country
            from customers
        ) countries right join products on country = origin
    ) t4 on t3.country = t4.country
) t5
group by country;