<template>
  <NPageHeader title="Docentes" subtitle="Revisar, crear, editar y eliminar docentes">
    <template #extra>
      <NSpace>
        <NButton type="primary" @click="showModal">
          <template #icon>
            <NIcon>
              <Plus />
            </NIcon>
          </template>

          Crear nuevo
        </NButton>
      </NSpace>
    </template>

    <NDataTable :columns="columns" :data="data" pagination />
  </NPageHeader>

  <FormView v-model="show" @update:model-value="(value: boolean) => (show = value)" />
</template>

<script setup lang="ts">
import { LockOff, LockAccess, Pencil, Plus, Trash } from '@vicons/tabler'
import { NButton, NDataTable, NIcon, NPageHeader, NSpace, type DataTableColumns } from 'naive-ui'
import { h, onMounted, ref } from 'vue'
import FormView from './FormView.vue'

const show = ref(false)

const showModal = () => {
  show.value = true
}

interface Teacher {
  name: string
  email: string
  status: boolean
}

const data = ref<Teacher[]>([])

const columns: DataTableColumns<Teacher> = [
  {
    title: 'Nombre',
    key: 'name'
  },
  {
    title: 'Correo',
    key: 'email'
  },
  {
    title: 'Acciones',
    key: 'actions',
    width: 175,
    className: 'flex justify-between',
    render: (row) => {
      return [
        h(
          NButton,
          {
            type: 'info',
            tertiary: true,
            onClick: () => {
              console.log('Edit', row)
            }
          },
          {
            icon: () => h(NIcon, null, { default: () => (row.status ? h(LockOff) : h(LockAccess)) })
          }
        ),
        h(
          NButton,
          {
            type: 'info',
            tertiary: true,
            onClick: () => {
              console.log('Edit', row)
            }
          },
          {
            icon: () => h(NIcon, null, { default: () => h(Pencil) })
          }
        ),
        h(
          NButton,
          {
            type: 'error',
            tertiary: true,
            onClick: () => {
              console.log('Delete', row)
            }
          },
          {
            icon: () => h(NIcon, null, { default: () => h(Trash) })
          }
        )
      ]
    }
  }
]

onMounted(() => {
  setTimeout(() => {
    data.value = [
      {
        name: 'Juan Pérez',
        email: 'juan@per.com',
        status: true
      },
      {
        name: 'María González',
        email: 'email@dewv.com',
        status: false
      },
      {
        name: 'Pedro Rodríguez',
        email: 'pedro@dev.com',
        status: true
      }
    ]
  }, 1000)
})
</script>
