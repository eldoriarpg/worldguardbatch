# WorldGuardBatch

Plugin to execute batch commands in WorldGuard

## Commands
### Membership
#### Remove Player

- Remove Player from all Regions\
/wgb mrem all    [playerToRemove]
- Remove Player as Member from all Regions\
/wgb mrem member [playerToRemove]
- Remove Player as Owner from all Regions\
/wgb mrem owner  [playerToRemove]

- Remove Player from all Regions with name pattern\
/wgb mrem all    [playerToRemove] count [name] [count1] [count2]
- Remove Player as Member from Regions with name pattern\
/wgb mrem member [playerToRemove] count [name] [count1] [count2]
- Remove Player as Owner from Regions with name pattern\
/wgb mrem owner  [playerToRemove] count [name] [count1] [count2]

- Remove Player from all Regions with name pattern\
/wgb mrem all    [playerToRemove] regex [name] [count1] [count2]
- Remove Player as Member from Regions with name pattern\
/wgb mrem member [playerToRemove] regex [name] [count1] [count2]
- Remove Player as Owner from Regions with name pattern\
/wgb mrem owner  [playerToRemove] regex [name] [count1] [count2]

- Remove Player on all Regions owned by player\
/wgb mrem all    [playerToRemove] owner [owner]
- Remove Player as Member on all Regions owned by player\
/wgb mrem member [playerToRemove] owner [owner]
- Remove Player as Owner on all Regions owned by player\
/wgb mrem owber  [playerToRemove] owner [owner]

#### Add Player
- Add Player as Member on all Regions\
/wgb madd member [playerToRemove]
- Add Player as Owner from all Regions\
/wgb madd owner  [playerToRemove]

- Add Player as Member on Regions with name pattern\
/wgb madd member [playerToRemove] count [name] [count1] [count2]
- Add Player as Owner on Regions with name pattern\
/wgb madd owner  [playerToRemove] count [name] [count1] [count2]

- Add Player as Member on Regions with name pattern\
/wgb madd member [playerToRemove] regex [name]
- Add Player as Owner on Regions with name pattern\
/wgb madd owner  [playerToRemove] regex [name]

- Add Player as Member on all Regions owned by player\
/wgb madd member [playerToRemove] owner [owner]
- Add Player as Owner on all Regions owned by player\
/wgb madd owner  [playerToRemove] owner [owner]


### Priority
- Set priority on all Regions\
/wgb prio [priority]
- Set priority on all Regions with parent\
/wgb prio [priority] parent [regionid]
- Set priority on all Regions with pattern\
/wgb prio [priority] count [name] [count1] [count2]
- Set priority on all regions with pattern\
/wgb prio [priority] count [name] regex [name]


### Parent/Inheritance
- Set parent on all Regions\
/wgb pset [parent]
- Set parent on all Regions with pattern\
/wgb pset [parent] regex [name]
- Set parent on all Regions with pattern\
/wgb pset [parent] count [name]

- Set parent to region on all Regions with pattern\
/wgb pset [child] [parent] count [name]
- Set parent to region on all Regions with pattern\
/wgb pset [child] [parent] regex [name]

- Remove all childs from parent
/wgb crem [parent]
- Remove all child from parent with pattern\
/wgb crem [parent] count [name]
- Remove all child from parent with pattern\
/wgb crem [parent] regex [name]


- Remove parent from all regions with pattern\
/wgb prem count [name]
- Remove parent from all regions with pattern\
/wgb prem regex [name]
