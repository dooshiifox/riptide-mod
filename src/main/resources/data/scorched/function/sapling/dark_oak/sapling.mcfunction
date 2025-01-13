particle dust_color_transition{from_color:[0.910,0.667,0.102],scale:0.8,to_color:[0.459,0.220,0.220]} ~ ~-.5 ~ .2 0 .2 0 1 normal
execute if block ~ ~ ~ dark_oak_sapling[stage=1] run scoreboard players add @s scorched_count 1
execute if block ~ ~ ~ dark_oak_sapling[stage=1] run setblock ~ ~ ~ dark_oak_sapling[stage=0]



execute if score @s scorched_count matches 2.. run function scorched:sapling/dark_oak/grow



execute unless block ~ ~ ~ dark_oak_sapling run kill @s