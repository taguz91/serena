import SinglePageLayout from '@/components/layouts/SinglePageLayout.vue';
<template>
  <SinglePageLayout>
    <div class="flex flex-col items-center justify-center h-3/5 m-auto w-full">
      <AppIcon />

      <p class="my-6 mx-auto w-3/5 text-center text-xl font-semibold text-slate-500">
        Bienvenido a Serena, una aplicación web para la gestión del estado de animo y la salud
        mental de los estudiantes.
      </p>

      <hr class="w-full my-2 bg-slate-700" />

      <p class="text-sm text-slate-400 w-4/5 text-center my-2">
        Tu cuenta esta siendo validada, cuando un administrados verifique tus datos podrás continuar
        al sistema.
      </p>
    </div>
  </SinglePageLayout>
</template>

<script lang="ts" setup>
import SinglePageLayout from '@/components/layouts/SinglePageLayout.vue'
import AppIcon from '@/components/shared/AppIcon.vue'
</script>
