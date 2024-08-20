export const createRouterPath = (path: string): string => {
    const routerPath = import.meta.env.BASE_URL + path;
    return routerPath.replaceAll("//", "/");
};