<template>
  <main class="bg-slate-100 min-h-screen w-screen flex flex-col">
    <div class="flex-1 justify-center align-middle w-full">
      <slot />
    </div>

    <FooterSimple />
  </main>
</template>

<script setup lang="ts">
import FooterSimple from '../shared/FooterSimple.vue'

//
</script>
