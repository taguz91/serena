<template>
  <PublicLayout>
    <SmallSpinner v-if="isLoading" />

    <div v-else class="my-10">
      <p class="font-bold text-lg">Vamos a revisar si tienes una inscripción vigente</p>

      <hr class="my-2" />

      <div class="w-full mt-10">
        <NForm ref="formRef" :rules="rules">
          <NFormItem label="Identificación/Cédula">
            <NInput v-model:value="identification" type="text" placeholder="0100000000" />
          </NFormItem>
        </NForm>

        <div class="flex justify-end">
          <NButton
            :disabled="identification === ''"
            type="primary"
            :loading="loading || isLoadingStudent"
            @click="handleValidateButtonClick"
          >
            Buscar
          </NButton>
        </div>
      </div>

      <hr class="my-2" />

      <div v-if="checked === 'error'" class="flex justify-center">
        <NForm ref="formStudent" :model="studentForm">
          <NFormItem path="identification" label="Identificación">
            <NInput v-model:value="studentForm.identification" type="text" disabled />
          </NFormItem>

          <NFormItem path="name" label="Nombre">
            <NInput v-model:value="studentForm.name" type="text" placeholder="John" />
          </NFormItem>

          <NFormItem path="lastname" label="Apellido">
            <NInput v-model:value="studentForm.lastname" type="text" placeholder="Doe" />
          </NFormItem>

          <NFormItem path="gender" label="Sexo">
            <NRadio
              :checked="studentForm.gender === 'H'"
              value="H"
              name="gender"
              @change="handleChangeGender"
            >
              Hombre
            </NRadio>
            <NRadio
              :checked="studentForm.gender === 'M'"
              value="M"
              name="gender"
              @change="handleChangeGender"
            >
              Mujer
            </NRadio>
          </NFormItem>

          <div class="flex justify-end">
            <NButton
              :disabled="identification === ''"
              type="primary"
              :loading="isCreating"
              @click="handleCreateStudent"
            >
              Inscribirse
            </NButton>
          </div>
        </NForm>
      </div>

      <div v-if="checked === 'success'" class="">
        <p class="font-bold text-xl">{{ student?.name }}</p>

        <p>
          <span class="font-semibold"> El registro se creo: </span>
          <span>{{ student?.createdAt }}</span>
        </p>

        <hr class="my-2" />

        <div v-if="confirmation">
          <p class="font-semibold">Gracias por confirmar tu inscripción</p>
        </div>

        <div v-else class="flex justify-center gap-2">
          <NButton
            v-if="checkedExistInscription === 'error'"
            type="primary"
            @click="duplicateRegister"
          >
            Confirmar inscripción
          </NButton>

          <p v-else>
            <span class="font-semibold"> Ya tienes una inscripción vigente </span>
            {{ registerStudent?.createdAt }}
          </p>

          <NButton type="primary" @click="redirectToRegister"> Actualizar fotografía </NButton>
        </div>
      </div>
    </div>
  </PublicLayout>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'

import { useRegister } from '@/composable/register/useRegister'
import SmallSpinner from '@/components/shared/SmallSpinner.vue'
import PublicLayout from '@/components/layouts/PublicLayout.vue'
import { NButton, NForm, type FormInst, type FormRules, NFormItem, NInput, NRadio } from 'naive-ui'
import { ref, watch } from 'vue'
import {
  useExistsRegisterStudentInscription,
  useRegisterStudentInscription
} from '@/composable/register/useRegisterStudentInscription'
import { useRegisterStudentDuplicate } from '@/composable/register/useRegisterStudentDuplicate'
import { useStudentInscription } from '@/composable/students/useStudentInscription'

const route = useRoute()
const router = useRouter()
const confirmation = ref(false)
const { duplicate } = useRegisterStudentDuplicate(route.params.id.toString(), () => {
  confirmation.value = true
})

const { isLoading } = useRegister(route.params.id.toString())
const {
  isCreating,
  save: saveStudent,
  studentForm
} = useStudentInscription(route.params.id.toString())

const rules: FormRules = {
  identification: {
    required: true,
    trigger: ['blur'],
    message: 'Ingresa la cédula'
  }
}

const formRef = ref<FormInst | null>()
const formStudent = ref<FormInst | null>()
const loading = ref(false)
const checked = ref<'error' | 'success' | 'pending' | ''>('')
const checkedExistInscription = ref<'error' | 'success' | 'pending' | ''>('')
const identification = ref<string>('')

const handleValidateButtonClick = async (e: MouseEvent) => {
  e.preventDefault()

  formRef.value?.validate(async (errors) => {
    loading.value = true
    checked.value = ''
    confirmation.value = false
    if (!errors) {
      const status = await search()

      checked.value = status

      if (status == 'error') {
        studentForm.value.identification = identification.value
      } else if (student.value) {
        checkedExistInscription.value = await searchRegisterStudent()
      } else {
        studentForm.value.identification = identification.value
        checkedExistInscription.value = 'error'
        checked.value = 'error'
      }

      loading.value = false
    }
  })
}

const handleCreateStudent = async (e: MouseEvent) => {
  e.preventDefault()

  formStudent.value?.validate(async (errors) => {
    if (!errors) {
      saveStudent()
    }
  })
}

const handleChangeGender = (value: Event) => {
  studentForm.value.gender = (value.target as HTMLInputElement).value
}

const {
  isLoading: isLoadingStudent,
  student,
  search
} = useRegisterStudentInscription(identification)

watch(student, () => {
  if (student) {
    console.log(student.value)
  } else {
    console.log('No student')
  }
})

const { registerStudent, search: searchRegisterStudent } = useExistsRegisterStudentInscription(
  route.params.id.toString(),
  student
)

const redirectToRegister = () => {
  router.push({
    name: 'public-register-inscription-photo',
    params: {
      id: route.params.id.toString(),
      idStudent: student.value?.id
    }
  })
}

const duplicateRegister = () => {
  duplicate(student.value?.id!)
}
</script>
