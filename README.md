# ItemBuilder
Create advanced ItemStacks with just one line of code.  
  
### Standart ItemBuilder API Usage
1. Create a Instance of the ItemBuilder: `ItemBuilder builder = new ItemBuilder(Material.GLOWSTONE);`  
2. Modify the ItemStack using one of the many methods, for example: `builder.displayname("§6ItemBuiler Glowstone");`  
3. When you are finish modifing it, use `builder.build();` to get the Bukkit ItemStack back  
You can also do all steps in just one line: `ItemStack item = new ItemBuilder(Material.GLOWSTONE).displayname("§6ItemBuilder Glowstone").build();`  
  
### NBT Tags Writing / Reading (Advanced)
1. Create a Instance of the ItemBuilder `ItemBuilder builder = new ItemBuilder(Material.DIAMOND_SWORD);`  
2. Access the `Unsafe` Class using the `builder.unsafe();` Method
3. Add/Remove NBT Tags using `builder.unsafe().addString("Key", "Value");` or `builder.unsafe().removeString("Key", "Value");`
4. Go back to the ItemBuilder Class using the `builder();` Method in `Unsafe` and `build();` the ItemStack.  
You can also do all steps in just one line: `ItemStack item = new ItemBuilder(Material.DIAMOND_SWORD).unsafe().addString("Key", "Value").builder().build();` 
  
### Json and Config Writing / Reading (Advanced)
1. Create a Instance of the ItemBuilder and set anything you need.
2. Convert it to a JSON String using `builder.toJson()` or to a Config Path using `builder.toConfig(MyPlugin.getInstance().getConfig(), "my.custom.item").
3. TIP! The Methods `fromJson`, `toJson`, `fromConfig` and `toConfig` are static. You can access them all using `ItemBuilder.<Method>` without creating a Instance of the ItemBuilder.
  
## Maven
Repository:  
```xml
<repositories>
    <repository>
        <id>acquized-repo</id>
        <url>http://repo.acquized.pw/maven/</url>
    </repository>
</repositories>
```  
Dependency:
```xml
<dependencies>
    <dependency>
        <groupId>cc.acquized</groupId>
        <artifactId>ItemBuilder</artifactId>
        <version>1.8</version>
    </dependency>
</dependencies>
```
  
* [JavaDocs](http://acquized.pw/docs/ItemBuilder/)
* [Spigot Page](https://www.spigotmc.org/resources/itembuilder.16786/)
