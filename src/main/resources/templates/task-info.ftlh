<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>Задача: ${task.title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
</head>

<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-9">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <a href="/" class="btn btn-secondary btn-sm"><i class="bi bi-arrow-left"></i> На главную</a>
                <form action="/task/delete/${task.id}" method="post">
                    <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Удалить задачу?')">
                        <i class="bi bi-trash"></i> Удалить задачу
                    </button>
                </form>
            </div>

            <div class="card shadow-sm mb-4">
                <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
                    <h3 class="card-title mb-0 text-primary">${task.title}</h3>
                    <button type="button" class="btn btn-outline-primary btn-sm" data-bs-toggle="modal" data-bs-target="#editTitleModal">
                        <i class="bi bi-pencil-square"></i> Редактировать заголовок
                    </button>
                </div>

                <div class="card-body">
                    <div class="row mb-3">
                        <div class="col-sm-3 text-muted">Описание:</div>
                        <div class="col-sm-9 d-flex justify-content-between align-items-start bg-light p-3 rounded h-auto">
                            <div class="text-break" style="white-space: pre-wrap;">${task.description}</div>
                            <button type="button" class="btn btn-link btn-sm text-secondary ms-2" data-bs-toggle="modal" data-bs-target="#editDescriptionModal">
                                <i class="bi bi-pencil"></i>
                            </button>
                        </div>
                    </div>

                    <div class="row mb-3 align-items-center">
                        <div class="col-sm-3 text-muted">Статус:</div>
                        <div class="col-sm-9">
                            <form action="/task/${task.id}/status-update" method="post" class="d-flex align-items-center gap-2">
                                <input type="hidden" name="_method" value="patch">
                                <select name="status" class="form-select form-select-sm w-auto">
                                    <#list allStatuses as statusOpt>
                                        <option value="${statusOpt}" <#if statusOpt == task.status>selected</#if>>${statusOpt}</option>
                                    </#list>
                                </select>
                                <button type="submit" class="btn btn-primary btn-sm">Обновить</button>
                            </form>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-3 text-muted">Создана:</div>
                        <div class="col-sm-9 text-muted small">${task.createdAt}</div>
                    </div>
                </div>
            </div>

            <div class="card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title mb-4"><i class="bi bi-paperclip"></i> Вложения</h5>
                    <ul class="list-group list-group-flush mb-4">
                        <#list attachments as attachment>
                            <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                <div>
                                    <i class="bi bi-file-earmark-text text-primary me-2"></i>
                                    <a href="/api/attachment/${attachment.attachmentId}" class="text-decoration-none">${attachment.fileName}</a>
                                </div>
                                <form action="/api/attachment/delete/${attachment.attachmentId}" method="post">
                                    <button type="submit" class="btn btn-link text-danger p-0"><i class="bi bi-x-circle-fill"></i></button>
                                </form>
                            </li>
                        <#else>
                            <p class="text-muted small">Файлов пока нет.</p>
                        </#list>
                    </ul>
                    <form action="/task/upload/${task.id}" method="post" enctype="multipart/form-data" class="input-group">
                        <input type="file" name="file" class="form-control form-control-sm">
                        <button class="btn btn-primary btn-sm" type="submit">Загрузить</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editDescriptionModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Редактировать описание</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <form action="/task/${task.id}/desc-update" method="post">
                <div class="modal-body">
                    <textarea class="form-control" name="task-description" rows="5">${task.description}</textarea>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="editTitleModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Новое название</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <form action="/task/${task.id}/task-name-update" method="post">
                <div class="modal-body">
                    <input type="text" class="form-control" name="task-name" value="${task.title}">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>