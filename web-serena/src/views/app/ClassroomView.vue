<template>
  <AppLayout>
    <template #side>
      <StudentList />
    </template>

    <DetailContainer current="Clase">
      <GraphContainer title="Resumen general">
        <div class="flex justify-between p-5">
          <div class="h-52 w-52 rounded-full bg-red-300 flex justify-center items-center">
            <p>Grafico</p>
          </div>

          <div class="mr-10">
            <h2 class="text-xl font-bold mb-2">Estados</h2>
            <p><span class="font-bold text-xl">10</span> Feliz</p>
            <p><span class="font-bold text-xl">2</span> Molestos</p>
          </div>
        </div>
      </GraphContainer>
    </DetailContainer>
  </AppLayout>
</template>

<script setup lang="ts">
import AppLayout from '@/components/layouts/AppLayout.vue'

import StudentList from '@/components/shared/StudentList.vue'
import DetailContainer from '@/components/containers/DetailContainer.vue'
import GraphContainer from '@/components/containers/GraphContainer.vue'
</script>
