export interface ServerStateChange {
    server: ServerHandle;
    state: number;
}

export interface ServerHandle {
    id: string;
    type: string;
}