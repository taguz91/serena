<template>
  <AppLayout>
    <template #side>
      <div class="p-4">
        <p class="font-bold mb-3">Recomendación</p>
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Provident, voluptate aperiam
          debitis aliquam tempore molestiae quidem. Quas doloremque rerum veritatis placeat ipsum
          asperiores est accusamus. Ullam consectetur ex architecto tenetur.
        </p>

        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Provident, voluptate aperiam
          debitis aliquam tempore molestiae quidem. Quas doloremque rerum veritatis placeat ipsum
          asperiores est accusamus. Ullam consectetur ex architecto tenetur.
        </p>
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Provident, voluptate aperiam
          debitis aliquam tempore molestiae quidem. Quas doloremque rerum veritatis placeat ipsum
          asperiores est accusamus. Ullam consectetur ex architecto tenetur.
        </p>
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Provident, voluptate aperiam
          debitis aliquam tempore molestiae quidem. Quas doloremque rerum veritatis placeat ipsum
          asperiores est accusamus. Ullam consectetur ex architecto tenetur.
        </p>

        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Provident, voluptate aperiam
          debitis aliquam tempore molestiae quidem. Quas doloremque rerum veritatis placeat ipsum
          asperiores est accusamus. Ullam consectetur ex architecto tenetur.
        </p>
      </div>
    </template>

    <DetailContainer current="Estudiante">
      <GraphContainer title="Resumen Maths">
        <div class="flex justify-between p-5">
          <div class="h-52 w-52 rounded-full bg-red-300 flex justify-center items-center">
            <p>Grafico</p>
          </div>

          <div class="mr-10">
            <h2 class="text-xl font-bold mb-2">Estados</h2>
            <p><span class="font-bold text-xl">3</span> Feliz</p>
            <p><span class="font-bold text-xl">2</span> Molestos</p>
          </div>
        </div>
      </GraphContainer>

      <GraphContainer title="Resumen Science">
        <div class="flex justify-between p-5">
          <div class="h-52 w-52 rounded-full bg-red-300 flex justify-center items-center">
            <p>Grafico</p>
          </div>

          <div class="mr-10">
            <h2 class="text-xl font-bold mb-2">Estados</h2>
            <p><span class="font-bold text-xl">3</span> Feliz</p>
            <p><span class="font-bold text-xl">2</span> Molestos</p>
          </div>
        </div>
      </GraphContainer>

      <GraphContainer title="Resumen Maths">
        <div class="flex justify-between p-5">
          <div class="h-52 w-52 rounded-full bg-red-300 flex justify-center items-center">
            <p>Grafico</p>
          </div>

          <div class="mr-10">
            <h2 class="text-xl font-bold mb-2">Estados</h2>
            <p><span class="font-bold text-xl">3</span> Feliz</p>
            <p><span class="font-bold text-xl">2</span> Molestos</p>
          </div>
        </div>
      </GraphContainer>
    </DetailContainer>
  </AppLayout>
</template>

<script setup lang="ts">
import DetailContainer from '@/components/containers/DetailContainer.vue'
import GraphContainer from '@/components/containers/GraphContainer.vue'
import AppLayout from '@/components/layouts/AppLayout.vue'
</script>
