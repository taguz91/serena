<template>
  <main class="bg-slate-50 min-h-screen flex justify-between">
    <SideNav title="Serena" class="w-20" />

    <main class="ml-20 flex-1 flex lg:flex-row flex-col">
      <div class="w-full">
        <slot />
      </div>

      <div v-if="$slots.side" class="w-full lg:w-[310px] border-l-2">
        <slot name="side" />
      </div>
    </main>
  </main>
</template>

<script setup lang="ts">
import SideNav from '../shared/SideNav.vue'
</script>
