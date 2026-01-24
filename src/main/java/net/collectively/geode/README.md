# Geode

## First steps

In order to set up Geode, we need to **hook** it to our mod. You may do that by calling `Geode#setHookedMod` passing the `MOD_ID` of your mod:

```java
import net.collectively.geode.Geode;
import net.fabricmc.api.ModInitializer;

public class MyMod implements ModInitializer {
    public static final String MOD_ID = "my_mod";

    @Override
    public void onInitialize() {
        // setHookedMod sets the id of the mod we want geode
        // to act on, allowing for easy indices etc.
        Geode.setHookedMod(MOD_ID); // [!code highlight]
    }
}
```

## Modules

Geode is composed of different modules that cover parts of minecraft mod development. With time you'll learn to use all of them in harmony for an enhanced coding experience.
Without further ado, here are the modules:

### Core

The main module in Geode. It contains a lot of different types and utilities, but is mainly made for math and utility methods for handling base java types like collections, etc. This module currently contains an advanced math class, many types such as a `double2`, `double3` or `couple2`s, and utility classes such as `CollectionHelper`.

### MC (Minecraft)

You may view this module as the "second core" of Geode, as it contains everything Minecraft related. This module currently contains:

#### Index

Interfaces declaring methods to easily register new elements in a minecraft registry.

- `BlockIndex`: Utility class to easily register `Block`.
- `BlockTagIndex`: Utility class to easily register `TagKey<Block>`.
- `BlockEntityIndex`: Utility class to easily register `BlockEntity`.
- `ItemIndex`: Utility class to easily register `Item`.
- `ItemTagIndex`: Utility class to easily register `TagKey<Item>`.
- `GroupIndex`: Utility class to easily register `ItemGroup`.
- `SoundIndex`: Utility class to easily register `SoundEvent`.
- `EntityTypeIndex`: Utility class to easily register `EntityType`.
- `HudElementIndex`: Utility class to easily register `HudElement` (**Client Only**).

#### Serialization

Interfaces related to data serialization in MC.

- `Readable`: An element which can read data from a `ReadView`.
- `Writable`: An element that can write data in a `WriteView`.
- `WriteReadUtil`: Interface containing helper methods for writing and reading from views.

#### Utility

Miscellaneous helper interfaces & classes.

- `BlockUtil`: Contains utilities to help with managing blocks.
- `DrawContextHelper`: Contains utilities to help with drawing on screen information.
- `EntityHelper`: Contains utilities to help with managing entities.
- `MatrixStackHelper`: Contains utilities to help with managing `MatrixStack`.
- `WorldUtil`: Contains utilities about a minecraft World.
- `GeodeText`: Represents a builder style formatted text that can be built back into a `MutableText`.
- `FormattedText`: Lightweight version of a `GeodeText` using a builder pattern to create text with only strings and `Formatting`.

### CCA (Cardinal Components API)

One choice that had to be made when making the library was whether to have it have a hard dependency on the amazing [Cardinal Components API](https://ladysnake.org/wiki/cardinal-components-api/) mod made by the folks over [Ladysnake](https://ladysnake.org/) or not. However, because of how often I end up using CCA in my projects, and how useful it is overall, I made the decision to have CCA be a hard dependency. This module currently contains:

#### `EnhancedComponent`

An interface representing a CCA `Component` with additional information about its target (what it's attached to), or its key for example, as well as methods to easily sync the `Component` with its target.

#### `EntityComponent`

Interface extending `EnhancedComponent` representing a `Component` with an `Entity` target.

#### `LivingEntityComponent`

Interface extending `EnhancedComponent` representing a `Component` with an `LivingEntity` target.

#### `PlayerEntityComponent`

Interface extending `EnhancedComponent` representing a `Component` with an `PlayerEntity` target.

### Debug

Module centred around debugging and drawing gizmos in the world to help with development. This library currently only contains one interface, `Draw` which allows the user to draw many gizmos in the world easily with methods such as `Draw#box(double3, double3)`, etc.

### Networking

Module about netcode and sharing data between the client and the server side, adapted to Minecraft's `CODEC` and general networking systems.

> [!WARNING] Work In Progress!
> This module currently does not contain any code.

### Datagen

Module all about generating annoying pesky assets for your mod instead of requiring you to do it by hand. This is an implementation layer over fabric's datagen layer.
This module currently contains:

#### `LanguageProvider`

A datagen class exposing methods for easy lang file data generation.

#### `ModelProvider`

A datagen class exposing methods for easy block model, item model, blockstate & item definition file data generation.

#### `SoundProvider`

A datagen class exposing methods for easy sound definition file data generation.

#### `GeodeRecipeProvider`

A datagen class exposing methods for easy crafting recipe file data generation.

> [!WARNING] Work In Progress!
> The recipe provider data generator is currently work in progress and does not offer any new functionality.
