<script setup lang="ts">
import PublicLayout from '@/components/layouts/PublicLayout.vue'
</script>

<template>
  <PublicLayout>
    <div class="flex flex-col w-full">
      <div class="flex justify-end">
        <RouterLink
          :to="{ name: 'home' }"
          class="text-white bg-black px-4 py-2 rounded-md mt-4 mr-4"
        >
          Regresar al inicio
        </RouterLink>
      </div>

      <div class="flex flex-col items-center justify-center h-full">
        <img src="/images/logo-bg-white.png" alt="logo" class="w-72 h-44 mb-4" />

        <h1 class="text-3xl font-bold mt-4">Desarrollado por:</h1>

        <div class="flex gap-x-8 mt-4 text-2xl">
          <div>
            <p class="text-lg">Johnny Garcia</p>
          </div>

          <div>
            <p class="text-lg">Armando Aucapiña</p>
          </div>
        </div>
      </div>
    </div>
  </PublicLayout>
</template>
