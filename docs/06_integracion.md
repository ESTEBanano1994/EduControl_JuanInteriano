# Fase 6.1 — Integración de ramas Feature a Develop

## Proyecto

**Nombre:** EduControl — Sistema de Matriculación Académica

**Objetivo:** Integrar las funcionalidades desarrolladas durante las fases anteriores y verificar el funcionamiento general del sistema.

## Ramas desarrolladas

| Rama                               | Funcionalidad                                    |
| ---------------------------------- | ------------------------------------------------ |
| feature/fase1-my-sql               | Base de datos MySQL                              |
| feature/fase2-git-flow             | Estructura del proyecto y GitFlow                |
| feature/fase3-base-core            | Conexión Singleton, SessionContext y excepciones |
| feature/fase4-modeo-dao            | Modelos, interfaces DAO e implementaciones       |
| feature/fase5-vistas-controladores | Login, menús, CRUD visual y prematrícula         |

## Integración

Se comprobó mediante el historial de Git que los cambios de las fases anteriores están presentes en la rama `develop`.

**Último commit integrado de la Fase 5:** `9f89ba3`

La integración final de la Fase 6 se realizará mediante un merge desde `feature/fase6-integracion-release` hacia `develop`.

## Pruebas de integración

Marcar los resultados únicamente después de comprobarlos:

- [ ] El proyecto compila mediante Clean and Build.
- [ ] El sistema inicia correctamente desde NetBeans.
- [ ] El login autentica usuarios registrados.
- [ ] El menú cambia según el rol del usuario.
- [ ] La gestión de cursos permite consultar, agregar, actualizar y eliminar.
- [ ] La prematrícula registra correctamente una sección.
- [ ] Se rechazan prematrículas duplicadas.
- [ ] Se validan cupos y prerrequisitos.
- [ ] La compilación termina sin advertencias.

## Resultado

**Estado:** Pendiente de verificación final.

**Observaciones:** Registrar aquí los resultados de las pruebas, advertencias y correcciones realizadas.

## Evidencia Git

**SHA-1 del merge de integración:** Pendiente de generar.
