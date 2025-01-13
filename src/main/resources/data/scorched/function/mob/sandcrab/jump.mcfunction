scoreboard players add @s scorched_count3 1




execute if score @s scorched_count3 matches 1..2 at @s if block ~ ~.8 ~ #scorched:permeable run tp @s ~ ~.4 ~
execute if score @s scorched_count3 matches 3..4 at @s if block ~ ~.8 ~ #scorched:permeable run tp @s ~ ~.2 ~


execute at @s positioned ~ ~.8 ~ if block ^ ^ ^.2 #scorched:permeable positioned ~ ~-.8 ~ if score @s scorched_count3 matches 3..6 at @s run tp @s ^ ^ ^.15



execute if score @s scorched_count3 matches 10.. at @s run scoreboard players reset @s scorched_count3