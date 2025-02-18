<template>
  <div class="py-4 px-10 bg-slate-100">
    <div class="flex justify-between">
      <p class="text-slate-500 text-lg font-semibold">
        Tecnología Superior Universitaria en Desarrollo de Software
      </p>

      <p class="text-lg text-slate-500">© {{ year }} Serena</p>
    </div>

    <hr class="my-1" />

    <div class="flex justify-between mt-2">
      <p class="text-sm text-slate-500 font-bold mb-1">Desarrollado por:</p>
      <p class="text-sm text-slate-500 flex flex-col">
        <span> Johnny Garcia </span>

        <span> Armando Aucapiña </span>
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

const year = computed(() => new Date().getFullYear())
</script>
