
execute if block ~ ~ ~ #scorched:permeable if block ~ ~-0.1 ~ #scorched:red_sand run particle block{block_state:"red_sand"} ~ ~ ~ .3 0 .3 0 4 force @a[distance=..32]
execute if block ~ ~ ~ #scorched:permeable if block ~ ~-0.1 ~ #scorched:sand run particle block{block_state:"sand"} ~ ~ ~ .3 0 .3 0 4 force @a[distance=..32]
execute if block ~ ~ ~ #scorched:permeable if block ~ ~-0.1 ~ #scorched:dirt run particle block{block_state:"dirt"} ~ ~ ~ .3 0 .3 0 4 force @a[distance=..32]
execute if block ~ ~ ~ #scorched:permeable if block ~ ~-0.1 ~ #scorched:stone run particle block{block_state:"stone"} ~ ~ ~ .3 0 .3 0 4 force @a[distance=..32]
execute unless block ~ ~ ~ #scorched:permeable positioned ~ ~1 ~ run function scorched:mob/desert_spider/particle_large