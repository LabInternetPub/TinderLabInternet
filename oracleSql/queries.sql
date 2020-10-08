select id, email, nickname, gender, attraction, passion from tinder_user;

select u.id as id, u.email as email, u.nickname as nickname, u.gender as gender, u.attraction as attraction, u.passion as passion,
l.creation_date as likes_creationDate, l.matched as likes_matched, l.match_date as likes_matchDate,
tu.id as likes_target_id, tu.email as likes_target_email, tu.nickname as likes_target_nickname, tu.gender as likes_target_gender,
tu.attraction as likes_target_attraction, tu.passion as likes_target_passion
from tinder_user u
left join tinder_like l on u.id = l.origin
left join tinder_user tu on l.origin = u.id and l.target = tu.id;