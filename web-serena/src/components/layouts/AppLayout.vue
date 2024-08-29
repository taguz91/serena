<template>
  <TopNav title="Serena" />

  <main class="bg-slate-100 min-h-screen w-screen flex">
    <div class="w-full">
      <slot />
    </div>

    <div class="w-[310px] border-l-2">
      <slot name="side" />
    </div>
  </main>
</template>

<script setup lang="ts">
import TopNav from '../shared/TopNav.vue'
</script>
