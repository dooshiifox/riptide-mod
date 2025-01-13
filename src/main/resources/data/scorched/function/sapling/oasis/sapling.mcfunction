particle dust_color_transition{from_color:[0.412,0.878,0.000],scale:0.8,to_color:[0.722,0.698,0.000]} ~ ~-.5 ~ .2 0 .2 0 1 normal
execute if block ~ ~ ~ oak_sapling[stage=1] run scoreboard players add @s scorched_count 1
execute if block ~ ~ ~ oak_sapling[stage=1] run setblock ~ ~ ~ oak_sapling[stage=0]



execute if score @s scorched_count matches 2.. run function scorched:sapling/oasis/grow


execute unless block ~ ~ ~ oak_sapling run kill @s